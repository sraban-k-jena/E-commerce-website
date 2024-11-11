package com.jt.e_commerce_website_backend.service.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.jt.e_commerce_website_backend.config.JwtProvider;
import com.jt.e_commerce_website_backend.constant.UserRole;
import com.jt.e_commerce_website_backend.model.Cart;
import com.jt.e_commerce_website_backend.model.Seller;
import com.jt.e_commerce_website_backend.model.User;
import com.jt.e_commerce_website_backend.model.VerificationCode;
import com.jt.e_commerce_website_backend.repository.CartRepository;
import com.jt.e_commerce_website_backend.repository.SellerRepository;
import com.jt.e_commerce_website_backend.repository.UserRepository;
import com.jt.e_commerce_website_backend.repository.VerfificationCodeRepository;
import com.jt.e_commerce_website_backend.request.LoginRequest;
import com.jt.e_commerce_website_backend.response.AuthResponse;
import com.jt.e_commerce_website_backend.response.SignupRequest;
import com.jt.e_commerce_website_backend.service.AuthService;
import com.jt.e_commerce_website_backend.service.EmailService;
import com.jt.e_commerce_website_backend.utils.OtpUtils;

@Service
public class AuthServiceImpl implements AuthService {

    private static final String SIGNING_KEY = "signing_";
    private static final String LOGIN_SUBJECT = "sraban bazzar login/signup OTP";
    private static final String LOGIN_SUCCESS_MESSAGE = "Login Success.";
    private static final String USER_NOT_FOUND = "User not found with email: ";
    private static final String WRONG_OTP = "Wrong OTP";
    private static final String USER_NOT_EXISTS = "User does not exist.";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private VerfificationCodeRepository verfificationCodeRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CustomUserServiceImpl customUserServiceImpl;

    @Autowired
    private SellerRepository sellerRepository;

    @Override
    public String createUser(SignupRequest req) throws Exception {
        VerificationCode verificationCode = verfificationCodeRepository.findByEmail(req.getEmail());

        if (verificationCode == null || !verificationCode.getOtp().equals(req.getOtp())) {
            throw new Exception(WRONG_OTP);
        }

        User user = userRepository.findByEmail(req.getEmail());
        if (user == null) {
            User createUser = new User();
            createUser.setEmail(req.getEmail());
            createUser.setFullName(req.getFullName());
            createUser.setRole(UserRole.ROLE_CUSTOMER);
            createUser.setMobile("7381942381");
            createUser.setPassword(passwordEncoder.encode(req.getOtp()));

            user = userRepository.save(createUser);

            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(UserRole.ROLE_CUSTOMER.toString()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(req.getEmail(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Pass the email to generate the token
        return jwtProvider.generateToken(authentication, user.getEmail());
    }

    @Override
    public void sentLoginOtp(String email, UserRole role) throws Exception {
        if (email.startsWith(SIGNING_KEY)) {
            email = email.substring(SIGNING_KEY.length());

            if (role != null && role.equals(UserRole.ROLE_SELLER)) { // Add null check for role
                Seller seller = sellerRepository.findByEmail(email);

                if (seller == null) {
                    throw new Exception("Seller not found.");
                }
            } else {
                User user = userRepository.findByEmail(email);
                if (user == null) {
                    throw new Exception(USER_NOT_EXISTS);
                }
            }
        }

        VerificationCode existingCode = verfificationCodeRepository.findByEmail(email);
        if (existingCode != null) {
            verfificationCodeRepository.delete(existingCode);
        }

        String otp = OtpUtils.generateOtp();
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);

        verfificationCodeRepository.save(verificationCode);

        String message = "Your login/signup OTP is - " + otp;
        emailService.sendVerificationOtpEmail(email, otp, LOGIN_SUBJECT, message);
    }

    @Override
    public AuthResponse signing(LoginRequest req) throws Exception {
        String username = req.getEmail();
        String otp = req.getOtp();

        Authentication authentication = authenticate(username, otp);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Pass the email to generate the token
        String token = jwtProvider.generateToken(authentication, username);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage(LOGIN_SUCCESS_MESSAGE);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roleName = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();
        authResponse.setRole(UserRole.valueOf(roleName));

        return authResponse;
    }

    private Authentication authenticate(String username, String otp) throws Exception {
        UserDetails userDetails = customUserServiceImpl.loadUserByUsername(username);

        String SELLER_PREFIX = "seller_";

        if (username.startsWith(SELLER_PREFIX)) {
            username = username.substring(SELLER_PREFIX.length());
        }

        if (userDetails == null) {
            throw new BadCredentialsException(USER_NOT_FOUND + username);
        }

        VerificationCode verificationCode = verfificationCodeRepository.findByEmail(username);
        if (verificationCode == null || !verificationCode.getOtp().equals(otp)) {
            throw new Exception(WRONG_OTP);
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}

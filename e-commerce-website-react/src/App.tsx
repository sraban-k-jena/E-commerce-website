import React from 'react';
import logo from './logo.svg';
import './App.css';
import { Button, ThemeProvider } from '@mui/material';
import AddShoppingCartIcon from '@mui/icons-material/AddShoppingCart';
import Navbar from './customer/components/Navbar/Navbar';
import customTheme from './Theme/customTheme';
import Home from './customer/pages/Home/Home';
import Product from './customer/pages/Product/Product';


function App() {
  return (
  
      <ThemeProvider theme={customTheme}>
        <div>
            <Navbar />
            {/* <Home /> */}
            <Product />
        </div>
      </ThemeProvider>
      
    
  );
}

export default App;

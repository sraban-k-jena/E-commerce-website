import React from 'react'

const CategoryGrid = () => {
  return (
    <div className='grid gap-4 grid-rows-12 grid-cols-12 lg:h-[600px] px-5 lg:px-20'>
      
      <div className='col-span-3 row-span-12 text-white'>
        <img
        className='w-full h-full object-cover object-top rounded-md'
        src="https://assets.myntassets.com/h_720,q_90,w_540/v1/assets/images/23807268/2023/6/29/9930b235-5318-4755-abbe-08f99e969e781688026636544LehengaCholi7.jpg" alt="Saree" />
      </div>

      <div className='col-span-2 row-span-6 text-white'>
        <img
        className='w-full h-full object-cover object-top rounded-md' 
        src="https://assets.myntassets.com/h_720,q_90,w_540/v1/assets/images/24651572/2023/8/25/4fbf6d8c-d093-46c5-a5a6-7dd67c0c76551692964752597HouseofPataudiMenTanFauxLeatherFormalSlipOnLoafers1.jpg" alt="Shoe" />
      </div>

      <div className='col-span-4 row-span-6 text-white'>
        <img 
        className='w-full h-full object-cover object-top rounded-md'
        src="https://images.pexels.com/photos/12730873/pexels-photo-12730873.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1" alt="Lehnga" />
      </div>

    <div className='col-span-3 row-span-12 text-white'>
        <img
        className='w-full h-full object-cover object-top rounded-md' 
        src="https://shreeman.in/cdn/shop/files/20_3cfbd5a3-ecb6-482a-b798-7ffd9de1c784.jpg?v=17120616748width=700%22" alt="" />
    </div>

    <div className='col-span-4 row-span-6 text-white'>
        <img 
        className='w-full h-full object-cover object-top rounded-md'
        src="https://media.istockphoto.com/id/1276740597/photo/indian-traditional-gold-necklace.jpg?b=1&s=612x612&w=0&k=20&c=S-QnNZKqf2u3L-GIaDiIinNRU74GBWQaIDwY7gYJboY=" alt="" />
    </div>

    <div className='col-span-2 row-span-6 text-white'>
        <img 
        className='w-full h-full object-cover object-top rounded-md'
        src="https://rukminim2.flixcart.com/image/612/612/xif0q/sandal/h/c/i/7-a-10-pink-7-abuza-pink-original-imahfb77jgenyhfh.jpeg?q=70" alt="" />
    </div>

    </div>
  )
}

export default CategoryGrid

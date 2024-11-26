import React from 'react'
import SimillarProductCard from './SimillarProductCard'

const SimillarProduct = () => {
  return (
    <div className='grid lg:grid-cols-6 md:grid-cols-4 sm:grid-cols-2 grid-cols-1 justify-between gap-4 gap-y-8'>
        {[1,1,1,1,1,1,1].map((item)=><SimillarProductCard/>)}
    </div>
  )
}

export default SimillarProduct

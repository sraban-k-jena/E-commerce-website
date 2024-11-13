import React from 'react'
import menLevelTwo from '../../../data/category/level two/menLevelTwo'
import womenLevelTwo from '../../../data/category/level two/womenLevelTwo'
import electronicsLevelTwo from '../../../data/category/level two/electronicsLevelTwo'
import fornitureLevelTwo from '../../../data/category/level two/fornitureLevelTwo'
import menLevelThree from '../../../data/category/level three/menLevelThree'
import womenLevelThree from '../../../data/category/level three/womenLevelThree'
import electronicsLevelThree from '../../../data/category/level three/electronicsLevelThree'
import fornitureLevelThree from '../../../data/category/level three/fornitureLevelThree'
import { Box } from '@mui/material'

const categoryTwo = {
    men : menLevelTwo,
    women : womenLevelTwo,
    electronics : electronicsLevelTwo,
    home_furniture : fornitureLevelTwo
}

const categoryThree = {
    men : menLevelThree,
    women : womenLevelThree,
    electronics : electronicsLevelThree,
    home_furniture : fornitureLevelThree
}

const CategorySheet = () => {
  return (
    <Box className="bg-white shadow-lg lg:h-[500px] overflow-y-auto">
        <div className='flex text-sm flex-wrap'>
            {/* {
                categoryTwo["men"]?.map((item)=><div>
                <p className='text-primary-color mb-5 font-semibold'>{item.name}</p>
                </div>)
            } */}
            {
    categoryTwo["men"]?.map((item, index) => (
        <div key={index}>
            <p className='text-primary-color mb-5 font-semibold'>{item.name}</p>
        </div>
    ))
}

        </div>
    </Box>
  )
}

export default CategorySheet

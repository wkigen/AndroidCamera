#include "utils.h"
#include "log.h"

namespace vk{

#define abs(x) (x>=0 ? x:(-x))

    void Utils::checkIsSkin(uint8_t* src,uint8_t* des, int width,int height)
    {
        if(src == nullptr || des == nullptr)
            return;

        for (int ii = 0; ii < height; ++ii) {
            for (int jj = 0; jj < width; ++jj) {

                //算出具体像素地址
                int offestDes = ii * width + jj;
                int offsetSrc = offestDes * 4;

                uint8_t Red, Green, Blue;
                Red = src[offsetSrc];
                Green = src[offsetSrc+1];
                Blue = src[offsetSrc+2];

                //检测是不是皮肤
                if (Red>95 && Green>40 && Blue>20 && Red > Green && Red > Blue &&
                        abs(Red - Green) > 15)
                {
                    des[offestDes]  = 255;
                }
                else
                {
                    des[offestDes]  = 0;
                }

            }
        }
    }

}
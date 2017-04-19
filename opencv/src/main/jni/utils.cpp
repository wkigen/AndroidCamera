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


    void Utils::integral(uint8_t* src,uint64_t* des ,uint64_t* desSqr, int width,int height){

        uint64_t *columnSum = new uint64_t[width];
        uint64_t *columnSumSqr = new uint64_t[width];

        columnSum[0] = src[0];
        columnSumSqr[0] = src[0] * src[0];

        for(int i = 1;i < width;i++){
            columnSum[i] = src[3*i];
            columnSumSqr[i] = src[3*i] * src[3*i];

            des[i] = columnSum[i];
            des[i] += des[i-1];
            desSqr[i] = columnSumSqr[i];
            desSqr[i] += desSqr[i-1];
        }

        for (int i = 1;i < height; i++){
            int offset = i * width;

            columnSum[0] += src[3*offset];
            columnSumSqr[0] += src[3*offset] * src[3*offset];

            des[offset] = columnSum[0];
            desSqr[offset] = columnSumSqr[0];

            for(int j = 1; j < width; j++){
                columnSum[j] += src[3*(offset+j)];
                columnSumSqr[j] += src[3*(offset+j)] * src[3*(offset+j)];

                des[offset+j] = des[offset+j-1] + columnSum[j];
                desSqr[offset+j] = desSqr[offset+j-1] + columnSumSqr[j];
            }
        }
        delete[] columnSum;
        delete[] columnSumSqr;
    }

}
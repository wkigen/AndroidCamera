#include "conver.h"
#include "log.h"

namespace vk{

    void Conver::YCbCrToRGBA(uint8_t* from, uint8_t* to, int len)
    {
        if (len < 1) return;
        int Red, Green, Blue;
        int Y, Cb, Cr;
        int i,offset;
        for(i = 0; i < len; i++)
        {
            offset = (i << 1) + i;
            Y = from[offset]; Cb = from[offset+1] - 128; Cr = from[offset+2] - 128;
            Red = Y + ((RGBRCrI * Cr + HalfShiftValue) >> Shift);
            Green = Y + ((RGBGCbI * Cb + RGBGCrI * Cr + HalfShiftValue) >> Shift);
            Blue = Y + ((RGBBCbI * Cb + HalfShiftValue) >> Shift);
            if (Red > 255) Red = 255; else if (Red < 0) Red = 0;
            if (Green > 255) Green = 255; else if (Green < 0) Green = 0;
            if (Blue > 255) Blue = 255; else if (Blue < 0) Blue = 0;
            offset = i << 2;
            to[offset] = (uint8_t)Blue;
            to[offset+1] = (uint8_t)Green;
            to[offset+2] = (uint8_t)Red;
        }
    }

    void Conver::RGBAToYCbCr(uint8_t* from, uint8_t* to, int len)
    {
        if (len < 1) return;
        int Red, Green, Blue;
        int i,offset;
        for(i = 0; i < len; i++)
        {
            offset = i << 2;
            Blue = from[offset]; Green = from[offset+1]; Red = from[offset+2];
            offset = (i << 1) + i;
            to[offset] = (uint8_t)((YCbCrYRI * Red + YCbCrYGI * Green + YCbCrYBI * Blue + HalfShiftValue) >> Shift);
            to[offset+1] = (uint8_t)(128 + ((YCbCrCbRI * Red + YCbCrCbGI * Green + YCbCrCbBI * Blue + HalfShiftValue) >> Shift));
            to[offset+2] = (uint8_t)(128 + ((YCbCrCrRI * Red + YCbCrCrGI * Green + YCbCrCrBI * Blue + HalfShiftValue) >> Shift));
        }
    }

}

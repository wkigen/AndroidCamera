#ifndef _UTILS_H_
#define _UTILS_H_

#include <stdio.h>

namespace vk{

    class Utils{

    public:

        static void checkIsSkin(uint8_t* src,uint8_t* des ,int width,int height);

        static void integral(uint8_t* src,uint64_t* des ,uint64_t* desSqr, int width,int height);
    };




}

#endif
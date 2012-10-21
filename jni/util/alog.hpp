#pragma once

#ifdef ANDROID

#include <android/log.h>

#define  LOG_TAG    "JYTKY"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)

#else

#include <cstdio>
#define LOGI printf
#define LOGE printf

#endif

typedef signed char byte;
typedef unsigned char ubyte;

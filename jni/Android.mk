LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

SRC:=$(wildcard jni/*.cpp jni/jee/*.cpp jni/render/*.cpp jni/andr/*.cpp)

LOCAL_MODULE    := jytky
LOCAL_SRC_FILES := $(patsubst jni/%,%,$(SRC))
LOCAL_LDLIBS    := -llog -landroid -lEGL -lGLESv2
LOCAL_STATIC_LIBRARIES := android_native_app_glue
LOCAL_CXXFLAGS += -Wall -Wextra -std=gnu++0x -Wno-abi -O2

include $(BUILD_SHARED_LIBRARY)

$(call import-module,android/native_app_glue)

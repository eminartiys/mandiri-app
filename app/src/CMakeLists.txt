# Sets the minimum version of CMake required to build your native library.
# This ensures that a certain set of CMake features is available to
# your build.

cmake_minimum_required(VERSION 3.4.1)

#set(LLVM_PATH /Users/adefruandta/Library/Android/sdk/ndk-bundle/toolchains/aarch64-linux-android-4.9/prebuilt/darwin-x86_64)
#link_directories(${LLVM_PATH}/lib)
#include_directories(${LLVM_PATH}/lib/clang/3.4/include)
#
#add_definitions(
#        -D__STDC_LIMIT_MACROS
#        -D__STDC_CONSTANT_MACROS
#)
#
#set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -fno-rtti -std=c++11")
#
#set(SOURCE_FILES main.cpp)

# Specifies a library name, specifies whether the library is STATIC or
# SHARED, and provides relative paths to the source code. You can
# define multiple libraries by adding multiple add_library() commands,
# and CMake builds them for you. When you build your app, Gradle
# automatically packages shared libraries with your APK.

add_library( # Specifies the name of the library.
#add_executable( # Specifies the name of the library.
        native-lib

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        main/cpp/native-lib.cpp )

#target_link_libraries(native-lib
#        clangFrontend
#        clangSerialization
#        clangDriver
#        clangParse
#        clangSema
#        clangAnalysis
#        clangAST
#        clangBasic
#        clangEdit
#        clangLex
#        clangTooling
#        )

#target_link_libraries(native-lib
#        LLVMX86AsmParser # MC, MCParser, Support, X86Desc, X86Info
#        LLVMX86Desc # MC, Support, X86AsmPrinter, X86Info
#        LLVMX86AsmPrinter # MC, Support, X86Utils
#        LLVMX86Info # MC, Support, Target
#        LLVMX86Utils # Core, Support
#        LLVMipo
#        LLVMScalarOpts
#        LLVMInstCombine
#        LLVMTransformUtils
#        LLVMipa
#        LLVMAnalysis
#        LLVMTarget
#        LLVMOption # Support
#        LLVMMCParser # MC, Support
#        LLVMMC # Object, Support
#        LLVMObject # BitReader, Core, Support
#        LLVMBitReader # Core, Support
#        LLVMCore # Support
#        LLVMSupport
#        )

#include(FindCurses)
#target_link_libraries(native-lib
#        pthread
#        z
#        dl
#        ${CURSES_LIBRARIES}
#        )
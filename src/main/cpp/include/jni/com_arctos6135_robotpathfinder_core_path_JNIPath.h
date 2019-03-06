/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_arctos6135_robotpathfinder_core_path_JNIPath */

#ifndef _Included_com_arctos6135_robotpathfinder_core_path_JNIPath
#define _Included_com_arctos6135_robotpathfinder_core_path_JNIPath
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_arctos6135_robotpathfinder_core_path_JNIPath
 * Method:    _construct
 * Signature: ([Lcom/arctos6135/robotpathfinder/core/JNIWaypoint;DI)V
 */
JNIEXPORT void JNICALL Java_com_arctos6135_robotpathfinder_core_path_JNIPath__1construct
  (JNIEnv *, jobject, jobjectArray, jdouble, jint);

/*
 * Class:     com_arctos6135_robotpathfinder_core_path_JNIPath
 * Method:    _destroy
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_arctos6135_robotpathfinder_core_path_JNIPath__1destroy
  (JNIEnv *, jobject);

/*
 * Class:     com_arctos6135_robotpathfinder_core_path_JNIPath
 * Method:    _setBaseRadius
 * Signature: (D)V
 */
JNIEXPORT void JNICALL Java_com_arctos6135_robotpathfinder_core_path_JNIPath__1setBaseRadius
  (JNIEnv *, jobject, jdouble);

/*
 * Class:     com_arctos6135_robotpathfinder_core_path_JNIPath
 * Method:    _setBackwards
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_com_arctos6135_robotpathfinder_core_path_JNIPath__1setBackwards
  (JNIEnv *, jobject, jboolean);

/*
 * Class:     com_arctos6135_robotpathfinder_core_path_JNIPath
 * Method:    at
 * Signature: (D)Lcom/arctos6135/robotpathfinder/math/Vec2D;
 */
JNIEXPORT jobject JNICALL Java_com_arctos6135_robotpathfinder_core_path_JNIPath_at
  (JNIEnv *, jobject, jdouble);

/*
 * Class:     com_arctos6135_robotpathfinder_core_path_JNIPath
 * Method:    derivAt
 * Signature: (D)Lcom/arctos6135/robotpathfinder/math/Vec2D;
 */
JNIEXPORT jobject JNICALL Java_com_arctos6135_robotpathfinder_core_path_JNIPath_derivAt
  (JNIEnv *, jobject, jdouble);

/*
 * Class:     com_arctos6135_robotpathfinder_core_path_JNIPath
 * Method:    secondDerivAt
 * Signature: (D)Lcom/arctos6135/robotpathfinder/math/Vec2D;
 */
JNIEXPORT jobject JNICALL Java_com_arctos6135_robotpathfinder_core_path_JNIPath_secondDerivAt
  (JNIEnv *, jobject, jdouble);

/*
 * Class:     com_arctos6135_robotpathfinder_core_path_JNIPath
 * Method:    wheelsAt
 * Signature: (D)Lcom/arctos6135/robotpathfinder/util/Pair;
 */
JNIEXPORT jobject JNICALL Java_com_arctos6135_robotpathfinder_core_path_JNIPath_wheelsAt
  (JNIEnv *, jobject, jdouble);

/*
 * Class:     com_arctos6135_robotpathfinder_core_path_JNIPath
 * Method:    _computeLen
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL Java_com_arctos6135_robotpathfinder_core_path_JNIPath__1computeLen
  (JNIEnv *, jobject, jint);

/*
 * Class:     com_arctos6135_robotpathfinder_core_path_JNIPath
 * Method:    _s2T
 * Signature: (D)D
 */
JNIEXPORT jdouble JNICALL Java_com_arctos6135_robotpathfinder_core_path_JNIPath__1s2T
  (JNIEnv *, jobject, jdouble);

/*
 * Class:     com_arctos6135_robotpathfinder_core_path_JNIPath
 * Method:    _t2S
 * Signature: (D)D
 */
JNIEXPORT jdouble JNICALL Java_com_arctos6135_robotpathfinder_core_path_JNIPath__1t2S
  (JNIEnv *, jobject, jdouble);

/*
 * Class:     com_arctos6135_robotpathfinder_core_path_JNIPath
 * Method:    _mirrorLeftRight
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_arctos6135_robotpathfinder_core_path_JNIPath__1mirrorLeftRight
  (JNIEnv *, jobject);

/*
 * Class:     com_arctos6135_robotpathfinder_core_path_JNIPath
 * Method:    _mirrorFrontBack
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_arctos6135_robotpathfinder_core_path_JNIPath__1mirrorFrontBack
  (JNIEnv *, jobject);

/*
 * Class:     com_arctos6135_robotpathfinder_core_path_JNIPath
 * Method:    _retrace
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_arctos6135_robotpathfinder_core_path_JNIPath__1retrace
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif

// clang-format off
/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_arctos6135_robotpathfinder_core_trajectory_BasicTrajectory */

#ifndef _Included_com_arctos6135_robotpathfinder_core_trajectory_BasicTrajectory
#define _Included_com_arctos6135_robotpathfinder_core_trajectory_BasicTrajectory
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_arctos6135_robotpathfinder_core_trajectory_BasicTrajectory
 * Method:    _construct
 * Signature: (DDDZ[Lcom/arctos6135/robotpathfinder/core/Waypoint;DII)V
 */
JNIEXPORT void JNICALL Java_com_arctos6135_robotpathfinder_core_trajectory_BasicTrajectory__1construct
  (JNIEnv *, jobject, jdouble, jdouble, jdouble, jboolean, jobjectArray, jdouble, jint, jint);

/*
 * Class:     com_arctos6135_robotpathfinder_core_trajectory_BasicTrajectory
 * Method:    _destroy
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_arctos6135_robotpathfinder_core_trajectory_BasicTrajectory__1destroy
  (JNIEnv *, jobject);

/*
 * Class:     com_arctos6135_robotpathfinder_core_trajectory_BasicTrajectory
 * Method:    _getMomentCount
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_arctos6135_robotpathfinder_core_trajectory_BasicTrajectory__1getMomentCount
  (JNIEnv *, jobject);

/*
 * Class:     com_arctos6135_robotpathfinder_core_trajectory_BasicTrajectory
 * Method:    _getMoments
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_arctos6135_robotpathfinder_core_trajectory_BasicTrajectory__1getMoments
  (JNIEnv *, jobject);

/*
 * Class:     com_arctos6135_robotpathfinder_core_trajectory_BasicTrajectory
 * Method:    _get
 * Signature: (D)Lcom/arctos6135/robotpathfinder/core/trajectory/BasicMoment;
 */
JNIEXPORT jobject JNICALL Java_com_arctos6135_robotpathfinder_core_trajectory_BasicTrajectory__1get
  (JNIEnv *, jobject, jdouble);

/*
 * Class:     com_arctos6135_robotpathfinder_core_trajectory_BasicTrajectory
 * Method:    _getPath
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_arctos6135_robotpathfinder_core_trajectory_BasicTrajectory__1getPath
  (JNIEnv *, jobject);

/*
 * Class:     com_arctos6135_robotpathfinder_core_trajectory_BasicTrajectory
 * Method:    totalTime
 * Signature: ()D
 */
JNIEXPORT jdouble JNICALL Java_com_arctos6135_robotpathfinder_core_trajectory_BasicTrajectory_totalTime
  (JNIEnv *, jobject);

/*
 * Class:     com_arctos6135_robotpathfinder_core_trajectory_BasicTrajectory
 * Method:    _mirrorLeftRight
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_arctos6135_robotpathfinder_core_trajectory_BasicTrajectory__1mirrorLeftRight
  (JNIEnv *, jobject);

/*
 * Class:     com_arctos6135_robotpathfinder_core_trajectory_BasicTrajectory
 * Method:    _mirrorFrontBack
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_arctos6135_robotpathfinder_core_trajectory_BasicTrajectory__1mirrorFrontBack
  (JNIEnv *, jobject);

/*
 * Class:     com_arctos6135_robotpathfinder_core_trajectory_BasicTrajectory
 * Method:    _retrace
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_arctos6135_robotpathfinder_core_trajectory_BasicTrajectory__1retrace
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif
// clang-format on

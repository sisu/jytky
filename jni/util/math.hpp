#pragma once
#include <type_traits>

template<class T, class A, class B>
typename std::common_type<T,A,B>::type clamp(T x, A low, B hi) {
	if (x<low) return low;
	if (hi<x) return hi;
	return x;
}

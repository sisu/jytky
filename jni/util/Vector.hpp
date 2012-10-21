#pragma once

#include "VecBase.hpp"
#include <cmath>

#define OP(op,ope)\
	template<class T, int N> inline Vector<T,N>& operator ope(Vector<T,N>& a, const Vector<T,N>& b) { for(int i=0; i<N; ++i) a[i] ope b[i]; return a; }\
	template<class T, int N> inline Vector<T,N> operator op(Vector<T,N> a, const Vector<T,N>& b) { return a ope b; }
OP(+,+=)
OP(-,-=)
#undef OP

#define OP(op,ope)\
	template<class T, int N> inline Vector<T,N>& operator ope(Vector<T,N>& a, T x) { for(int i=0; i<N; ++i) a[i] ope x; return a; }\
	template<class T, int N> inline Vector<T,N> operator op(Vector<T,N> a, T x) { return a ope x; }
OP(*,*=)
OP(/,/=)
#undef OP

template<class T, int N>
inline Vector<T,N> operator*(T x, Vector<T,N> a) { return a *= x; }
template<class T, int N>
inline Vector<T,N> operator-(Vector<T,N> a) { for(int i=0; i<N; ++i) a[i] = -a[i]; return a; }

template<class T, int N>
T dot(const Vector<T,N>& a, const Vector<T,N>& b) {
	T r=0;
	for(int i=0; i<N; ++i) r += a[i] * b[i];
	return r;
}

template<class T>
T cross(const Vector<T,2>& a, const Vector<T,2>& b) {
	return a[0]*b[1] - a[1]*b[0];
}
template<class T>
Vector<T,3> cross(const Vector<T,3>& a, const Vector<T,3>& b) {
	Vector<T,3> r;
	r[0] = a[1]*b[2] - a[2]*b[1];
	r[1] = a[2]*b[0] - a[0]*b[2];
	r[2] = a[0]*b[1] - a[1]*b[0];
	return r;
}
template<class T, int N> T norm2(const Vector<T,N>& a) { return dot(a,a); }
template<class T, int N> T norm(const Vector<T,N>& a) { return sqrt(norm2(a)); }
template<class T, int N> Vector<T,N> normalize(Vector<T,N> a) { return a/=norm(a); }
template<class T, int N> inline bool isZero(const Vector<T,N>& v) { for(int i=0; i<N; ++i) if (v[i]) return 0; return 1; }

#include <ostream>
template<class T, int N>
std::ostream& operator<<(std::ostream& o, const Vector<T,N>& v) {
	o << '(';
	for(int i=0; i<N; ++i) o<<' '<<v[i];
	o<<" )";
	return o;
}

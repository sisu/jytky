#pragma once

#include <utility>

template<class T>
struct Array {
	Array(): ptr(0), size(0), cap(0) {}
	explicit Array(int s): ptr(new T[s]), size(s), cap(s) {}
	Array(int s, const T& a): ptr(alloc(s)), size(s), cap(s) {
		for(int i=0; i<size; ++i) new(ptr+i) T(a);
	}
	Array(const Array& a) {
		size = cap = a.size;
		if (!size) {
			ptr = 0;
			return;
		}
		ptr = alloc(size);
		for(int i=0; i<a.size; ++i)
			new(ptr+i) T(a[i]);
	}
	~Array() { clear(); dealloc(ptr); }
	Array& operator=(Array a) {
		this->swap(a);
		return *this;
	}
	T& operator[](int i) { return ptr[i]; }
	const T& operator[](int i) const { return ptr[i]; }
	typedef T* iterator;
	iterator begin() { return ptr; }
	iterator end() { return ptr+size; }

	T& back() { return ptr[size-1]; }
	const T& back() const { return ptr[size-1]; }

	void push(const T& t) {
		if (size == cap) {
			reserve(size==0 ? 1 : 2*size);
		}
		new(ptr+size++) T(t);
	}
	void add(const T& t) {
		push(t);
	}
	template<class R>
	void addAll(R r) {
		addAll(r.begin(), r.end());
	}
	template<class I>
	void addAll(I b, I e) {
		for(; b!=e; ++b)
			add(*b);
	}
	void pop() {
		ptr[--size].~T();
	}
	void resize(int s) {
		if (s<=size) {
			for(int i=s; i<size; ++i)
				ptr[i].~T();
			size = s;
		} else {
			if (s>cap) reserve(s);
			for(int i=size; i<s; ++i) new(ptr+i) T;
			size = s;
		}
	}
	void reserve(int nc) {
		if (nc<=cap) return;
		T* tmp = alloc(nc);
		for(int i=0; i<size; ++i) new(tmp+i) T(ptr[i]);
		dealloc(ptr);
		ptr = tmp;
		cap = nc;
	}
	void clear() {
		for(int i=0; i<size; ++i)
			ptr[i].~T();
		size = 0;
	}
	void swap(Array& a) {
		using std::swap;
		swap(ptr, a.ptr);
		swap(size, a.size);
		swap(cap, a.cap);
	}

	T* ptr;
	int size;
	int cap;

	static T* alloc(int size) { return (T*)::operator new(sizeof(T)*size); }
	static void dealloc(void* ptr) { ::operator delete(ptr); }
};

namespace std {
template<class T>
void swap(Array<T>& a, Array<T>& b) {
	a.swap(b);
}
}

#include <ostream>

template<class T>
std::ostream& operator<<(std::ostream& o, const Array<T>& a) {
	o<<'[';
	for(int i=0; i<a.size; ++i) o<<' '<<a[i];
	o<<" ]";
	return o;
}

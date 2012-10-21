#pragma once

template<class I>
void assign(I) {}
template<class I, class X, class...A>
void assign(I arr, X head, A... tail) {
	*arr = head;
	assign(++arr, tail...);
}

#pragma once
#include "Array.hpp"

template<class K, class V>
struct ArrayMap {
	typedef std::pair<K,V> type;
	Array<type> data;

	V& operator[](const K& k) {
		for(int i=0; i<data.size; ++i)
			if (data[i].first==k)
				return data[i].second;
		type val = {k, V()};
		data.add(val);
		return data.back().second;
	}
};

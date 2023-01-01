package com.github.mckernant1.indexedmap

class IndexedEntry<K, V>(override val key: K, override val value: V) : Map.Entry<K, V>

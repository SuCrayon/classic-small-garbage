package skiplist

import "testing"

func prepare() *SkipList {
	s := New()
	oprs := readOprFromFile("skiplist.benchmark1.yaml")
	for i := range oprs {
		if oprs[i].Ot == OprInsertRange {
			insertRange(s, oprs[i], true)
		}
	}
	return s
}

func BenchmarkSearch(b *testing.B) {
	s := prepare()
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		s.Search(10000)
		s.Search(1277)
		s.Search(5893)
	}
}

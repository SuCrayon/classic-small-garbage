package skiplist

import "testing"

func prepare(maxLevel int, p float64, filename string) *SkipList {
	s := New(maxLevel, p)
	oprs := readOprFromFile(filename)
	for i := range oprs {
		if oprs[i].Ot == OprInsertRange {
			insertRange(s, oprs[i], true)
		}
	}
	return s
}

func Benchmark1_SkipList16_025_Search(b *testing.B) {
	s := prepare(16, 0.25, "skiplist.benchmark1.yaml")
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		s.Search(1000)
		s.Search(277)
		s.Search(593)
	}
}

func Benchmark1_SkipList16_05_Search(b *testing.B) {
	s := prepare(16, 0.5, "skiplist.benchmark1.yaml")
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		s.Search(1000)
		s.Search(277)
		s.Search(593)
	}
}

func Benchmark1_SkipList32_025_Search(b *testing.B) {
	s := prepare(32, 0.25, "skiplist.benchmark1.yaml")
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		s.Search(1000)
		s.Search(277)
		s.Search(593)
	}
}

func Benchmark1_SkipList32_05_Search(b *testing.B) {
	s := prepare(32, 0.5, "skiplist.benchmark1.yaml")
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		s.Search(1000)
		s.Search(277)
		s.Search(593)
	}
}

func Benchmark1_LinkedList_Search(b *testing.B) {
	s := prepare(1, 0, "skiplist.benchmark1.yaml")
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		s.Search(1000)
		s.Search(277)
		s.Search(593)
	}
}

func Benchmark2_SkipList16_05_Search(b *testing.B) {
	s := prepare(16, 0.5, "skiplist.benchmark2.yaml")
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		s.Search(10000)
		s.Search(1277)
		s.Search(5893)
	}
}

func Benchmark2_SkipList32_025_Search(b *testing.B) {
	s := prepare(32, 0.25, "skiplist.benchmark2.yaml")
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		s.Search(10000)
		s.Search(1277)
		s.Search(5893)
	}
}

func Benchmark2_SkipList32_05_Search(b *testing.B) {
	s := prepare(32, 0.5, "skiplist.benchmark2.yaml")
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		s.Search(10000)
		s.Search(1277)
		s.Search(5893)
	}
}

func Benchmark2_LinkedList_Search(b *testing.B) {
	s := prepare(1, 0, "skiplist.benchmark2.yaml")
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		s.Search(10000)
		s.Search(1277)
		s.Search(5893)
	}
}

func Benchmark3_SkipList16_05_Search(b *testing.B) {
	s := prepare(16, 0.5, "skiplist.benchmark3.yaml")
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		s.Search(100000)
		s.Search(21277)
		s.Search(55893)
	}
}

func Benchmark3_SkipList32_025_Search(b *testing.B) {
	s := prepare(32, 0.25, "skiplist.benchmark3.yaml")
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		s.Search(100000)
		s.Search(21277)
		s.Search(55893)
	}
}

func Benchmark3_SkipList32_05_Search(b *testing.B) {
	s := prepare(32, 0.5, "skiplist.benchmark3.yaml")
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		s.Search(100000)
		s.Search(21277)
		s.Search(55893)
	}
}

func Benchmark3_LinkedList_Search(b *testing.B) {
	s := prepare(1, 0, "skiplist.benchmark3.yaml")
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		s.Search(100000)
		s.Search(21277)
		s.Search(55893)
	}
}

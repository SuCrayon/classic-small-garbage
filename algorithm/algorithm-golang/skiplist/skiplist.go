package skiplist

import (
	"math/rand"
)

const (
	MaxLevel = 1
	P        = 0.5
)

type (
	Node struct {
		Key      float64
		Value    interface{}
		Forwards []*Node
	}

	SkipList struct {
		Head  *Node
		Level int
	}
)

func makeNode(key float64, value interface{}, level int) *Node {
	return &Node{
		Key:      key,
		Value:    value,
		Forwards: make([]*Node, level),
	}
}

func New() *SkipList {
	return &SkipList{
		Head:  makeNode(0, nil, MaxLevel),
		Level: 1,
	}
}

func randomLevel() int {
	level := 1
	for level < MaxLevel && rand.Float64() < P {
		level += 1
	}
	return level
}

func (s *SkipList) findPrev(key float64) (x *Node, update []*Node) {
	update = make([]*Node, MaxLevel)
	x = s.Head
	for i := s.Level - 1; i >= 0; i-- {
		for x.Forwards[i] != nil && x.Forwards[i].Key < key {
			x = x.Forwards[i]
		}
		update[i] = x
	}
	return
}

func (s *SkipList) Search(key float64) (interface{}, bool) {
	x, _ := s.findPrev(key)
	x = x.Forwards[0]
	if x == nil || x.Key != key {
		return nil, false
	}
	return x.Value, true
}

func (s *SkipList) Insert(key float64, value interface{}) {
	x, update := s.findPrev(key)
	x = x.Forwards[0]
	if x != nil && x.Key == key {
		x.Value = value
		return
	}

	level := randomLevel()
	if level > s.Level {
		for i := s.Level; i < level; i++ {
			update[i] = s.Head
		}
		s.Level = level
	}

	x = makeNode(key, value, level)
	for i := 0; i < level; i++ {
		x.Forwards[i] = update[i].Forwards[i]
		update[i].Forwards[i] = x
	}
}

func (s *SkipList) Delete(key float64) bool {
	x, update := s.findPrev(key)
	x = x.Forwards[0]
	if x == nil || x.Key != key {
		return false
	}

	for i := 0; i < s.Level; i++ {
		if update[i].Forwards[i] != x {
			break
		}
		update[i].Forwards[i] = x.Forwards[i]
	}

	for i := s.Level - 1; i >= 0; i-- {
		if s.Head.Forwards[i] != nil {
			break
		}
		s.Level--
	}

	return true
}

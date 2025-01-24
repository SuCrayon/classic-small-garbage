package skiplist

import (
	"fmt"
	"github.com/stretchr/testify/assert"
	"gopkg.in/yaml.v3"
	"math/rand/v2"
	"os"
	"testing"
	"time"
)

type (
	OprType string

	OprInput struct {
		Key        float64     `json:"key"  yaml:"key"`
		Value      interface{} `json:"value" yaml:"value"`
		RangeStart int         `json:"range_start" yaml:"range_start"`
		RangeEnd   int         `json:"range_end" yaml:"range_end"`
	}

	OprOutput struct {
		OK    bool        `json:"ok" yaml:"ok"`
		Value interface{} `json:"value" yaml:"value"`
	}

	Opr struct {
		Ot     OprType   `json:"ot" yaml:"ot"`
		Input  OprInput  `json:"input" yaml:"input"`
		Output OprOutput `json:"output" yaml:"output"`
	}
)

type (
	Inserter interface {
		Insert(key float64, value interface{})
	}
)

const (
	OprInsert      OprType = "insert"
	OprInsertRange OprType = "insert_range"
	OprSearch      OprType = "search"
	OprDelete      OprType = "delete"
	OprDeleteMany  OprType = "delete_many"
)

func Test_randomLevel(t *testing.T) {
	tests := []struct {
		name     string
		runTimes int
		assertFn func(res int)
	}{
		// TODO: Add test cases.
		{
			name:     "",
			runTimes: 100,
			assertFn: func(res int) {
				if res <= 0 || res > MaxLevel {
					t.Error("randomLevel run exceptionally")
				}
			},
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			for i := 0; i < tt.runTimes; i++ {
				res := randomLevel()
				t.Logf("randomLevel()=%v\n", res)
				tt.assertFn(res)
			}
		})
	}
}

func skipListDo(t *testing.T, oprs []*Opr) {
	s := New()
	for j := 0; j < len(oprs); j++ {
		opr := oprs[j]
		startTime := time.Now()
		if opr.Ot == OprInsert {
			s.Insert(opr.Input.Key, opr.Input.Value)
		} else if opr.Ot == OprDelete {
			assert.Equal(t, opr.Output.OK, s.Delete(opr.Input.Key))
		} else if opr.Ot == OprSearch {
			v, ok := s.Search(opr.Input.Key)
			assert.Equal(t, opr.Output.OK, ok)
			if ok {
				assert.EqualValues(t, opr.Output.Value, v)
			}
		} else if opr.Ot == OprInsertRange {
			insertRange(s, opr, true)
		} else {

		}

		endTime := time.Now()
		t.Logf("opr: %v, cost: %v\n", opr.Ot, endTime.Sub(startTime))
	}
}

func testWrapper(t *testing.T, fn func(*testing.T, []*Opr)) {
	tests := []struct {
		name     string
		runTimes int
		opr      []*Opr
	}{
		// TODO: Add test cases.
		{
			name:     "",
			runTimes: 1,
			opr:      readOprFromFile("skiplist.case1.yaml"),
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if tt.runTimes <= 0 {
				tt.runTimes = 100
			}
			for i := 0; i < tt.runTimes; i++ {
				fn(t, tt.opr)
			}
		})
	}
}

func TestSkipList(t *testing.T) {
	testWrapper(t, skipListDo)
}

func readOprFromFile(filename string) []*Opr {
	bs, err := os.ReadFile(fmt.Sprintf("testdata/%s", filename))
	if err != nil {
		panic(err)
	}
	var res []*Opr
	err = yaml.Unmarshal(bs, &res)
	if err != nil {
		panic(err)
	}
	return res
}

func insertRange(s Inserter, opr *Opr, shuffle bool) {
	kvs := make([]struct {
		k float64
		v interface{}
	}, 0, opr.Input.RangeEnd-opr.Input.RangeStart)
	for i := opr.Input.RangeStart; i < opr.Input.RangeEnd; i++ {
		kvs = append(kvs, struct {
			k float64
			v interface{}
		}{k: float64(i), v: i})
	}
	if shuffle {
		rand.Shuffle(len(kvs), func(i, j int) {
			kvs[i], kvs[j] = kvs[j], kvs[i]
		})
	}
	for i := range kvs {
		s.Insert(kvs[i].k, kvs[i].v)
	}
}

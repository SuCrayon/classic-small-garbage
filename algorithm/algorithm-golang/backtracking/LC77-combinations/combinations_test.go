package LC77_combinations

import (
	"testing"
)

func TestCombine(t *testing.T) {
	type args struct {
		n int
		k int
	}
	tests := []struct {
		name string
		args args
		want [][]int
	}{
		// TODO: Add test cases.
		{
			name: "case1",
			args: args{
				n: 4,
				k: 2,
			},
		},
		{
			name: "case1",
			args: args{
				n: 5,
				k: 3,
			},
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			got := Combine(tt.args.n, tt.args.k)
			t.Logf("Combine(%v, %v) = %v", tt.args.n, tt.args.k, got)
		})
	}
}

package LC17_letter_combinations_of_a_phone_number

import (
	"reflect"
	"testing"
)

func TestLetterCombinations(t *testing.T) {
	type args struct {
		digits string
	}
	tests := []struct {
		name string
		args args
		want []string
	}{
		// TODO: Add test cases.
		{
			name: "case1",
			args: args{
				digits: "23",
			},
			want: []string{
				"ad",
				"ae",
				"af",
				"bd",
				"be",
				"bf",
				"cd",
				"ce",
				"cf",
			},
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := LetterCombinations(tt.args.digits); !reflect.DeepEqual(got, tt.want) {
				t.Errorf("LetterCombinations() = %v, want %v", got, tt.want)
			}
		})
	}
}

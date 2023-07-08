package compare_version_numbers

import "testing"

func TestVersionCompare(t *testing.T) {
    type args struct {
        v1 string
        v2 string
    }
    tests := []struct {
        name string
        args args
        want int
    }{
        // TODO: Add test cases.
        {
            name: "case1",
            args: args{
                v1: "2.0.1",
                v2: "3.0.1",
            },
            want: -1,
        },
        {
            name: "case2",
            args: args{
                v1: "1.001",
                v2: "1.1",
            },
            want: 0,
        },
    }
    for _, tt := range tests {
        t.Run(tt.name, func(t *testing.T) {
            if got := VersionCompare(tt.args.v1, tt.args.v2); got != tt.want {
                t.Errorf("VersionCompare() = %v, want %v", got, tt.want)
            }
        })
    }
}

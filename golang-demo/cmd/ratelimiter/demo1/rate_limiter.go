package main

import (
    "context"
    "fmt"
    "golang.org/x/time/rate"
    "time"
)

func main() {
    limiter := rate.NewLimiter(rate.Every(time.Millisecond*1000), 1)
    for i := 0; i < 5; i++ {
        err := limiter.Wait(context.Background())
        if err != nil {
            panic(err)
        }
        fmt.Println(time.Now())
    }
}
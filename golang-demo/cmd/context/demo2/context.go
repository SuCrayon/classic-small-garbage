package main

import (
    "context"
    "fmt"
    "time"
)

/*
测试父级取消，子孙协程的退出情况
*/

const (
    timeFormatStr = "15:04:05"
)

func doSubTask(ctx context.Context, taskID string) {
    for {
        select {
        case <-ctx.Done():
            fmt.Printf("[%s] [%s] context done, err: %v\n", time.Now().Format(timeFormatStr), taskID, ctx.Err())
            fmt.Printf("[%s] [%s] sub task goroutine ending\n", time.Now().Format(timeFormatStr), taskID)
            return
        default:
            time.Sleep(1 * time.Second)
            fmt.Printf("[%s] [%s] handle loop ending\n", time.Now().Format(timeFormatStr), taskID)
        }
    }
}

func doTask(ctx context.Context, taskID string, hasSubTask bool) {
    for {
        select {
        case <-ctx.Done():
            fmt.Printf("[%s] [%s] context done, err: %v\n", time.Now().Format(timeFormatStr), taskID, ctx.Err())
            fmt.Printf("[%s] [%s] task goroutine ending\n", time.Now().Format(timeFormatStr), taskID)
            return
        default:
            if hasSubTask {
                go doSubTask(ctx, "sub-1")
            }
            time.Sleep(3 * time.Second)
            fmt.Printf("[%s] [%s] handle loop ending\n", time.Now().Format(timeFormatStr), taskID)
        }
    }
}

func main() {
    ctx, cancel := context.WithCancel(context.Background())
    subCtx, _ := context.WithCancel(ctx)


    go doTask(subCtx, "task-1", false)
    go doTask(subCtx, "task-2", true)

    time.Sleep(2 * time.Second)
    cancel()

    time.Sleep(1 * time.Minute)

}

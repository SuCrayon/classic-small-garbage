package main

import (
	"github.com/stretchr/testify/assert"
	"io/ioutil"
	"net/http"
	"net/http/httptest"
	"testing"
)

var (
	loginCase1JSON []byte
)

func init() {
	var (
		bs  []byte
		err error
	)
	bs, err = ioutil.ReadFile("testdata/access_token/login_case1.json")
	if err != nil {
		panic(err)
	}
	loginCase1JSON = bs

}

func Test_loginService_Login(t *testing.T) {
	const (
		uri = "/AUTH/api/v1/login"
	)
	// start mock server
	mux := http.NewServeMux()
	// mock target handler
	mux.HandleFunc(uri, func(w http.ResponseWriter, r *http.Request) {
		// assert http method
		/*if r.Method != http.MethodPost {
		    t.Errorf("unexpectd http method, want: post, got: %v", r.Method)
		}*/
		assert.Equal(t, http.MethodPost, r.Method)
		w.WriteHeader(http.StatusOK)
		w.Write(loginCase1JSON)
	})
	server := httptest.NewServer(mux)
	defer server.Close()

	// testing case
	accessToken, err := NewLoginService(server.URL + uri).Login(
		&LoginReq{
			Header: nil,
			Body: LoginReqBody{
				Username: "sucrayon",
				Password: "password",
			},
		},
	)
	// your assert code...
	assert.NoError(t, err)
	assert.Equal(t, "tokenA", accessToken)
}

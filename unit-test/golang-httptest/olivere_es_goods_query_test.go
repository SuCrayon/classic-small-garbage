package main

import (
	"github.com/stretchr/testify/assert"
	"io/ioutil"
	"net/http"
	"net/http/httptest"
	"testing"
)

var (
	goodsQueryCase1JSON []byte
)

func init() {
	var (
		bs  []byte
		err error
	)
	bs, err = ioutil.ReadFile("testdata/olivere_es_goods_query/goods_list_case1.json")
	if err != nil {
		panic(err)
	}
	goodsQueryCase1JSON = bs

}

func Test_goodsService_List(t *testing.T) {
	const (
		goodsSearchURI = "/goods/_search"
	)
	// start mock server
	mux := http.NewServeMux()
	// mock target handler
	mux.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(http.StatusOK)
	})
	mux.HandleFunc(goodsSearchURI, func(w http.ResponseWriter, r *http.Request) {
		w.WriteHeader(http.StatusOK)
		w.Write(goodsQueryCase1JSON)
	})
	server := httptest.NewServer(mux)
	defer server.Close()

	service, err := NewGoodsService(server.URL, "goods")
	assert.NoError(t, err)
	list, err := service.List()
	assert.NoError(t, err)
	t.Log(list)
}

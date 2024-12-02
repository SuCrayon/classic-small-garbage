package main

import (
	"context"
	"encoding/json"
	"github.com/olivere/elastic/v7"
)

type (
	GoodsService interface {
		List() (ListResp, error)
	}
	goodsService struct {
		url    string
		index  string
		client *elastic.Client
	}
	ListResp     []ListRespItem
	ListRespItem struct {
		Name  string  `json:"name"`
		Price float64 `json:"price"`
	}
)

func NewGoodsService(url, index string) (GoodsService, error) {
	client, err := elastic.NewClient(
		elastic.SetURL(url),
		elastic.SetSniff(false),
	)
	if err != nil {
		return nil, err
	}

	return &goodsService{
		url:    url,
		index:  index,
		client: client,
	}, nil
}

func (g *goodsService) List() (ListResp, error) {
	sr, err := g.client.Search(g.index).Do(context.TODO())
	if err != nil {
		return nil, err
	}
	resp := make([]ListRespItem, 0, sr.TotalHits())
	if sr.TotalHits() == 0 {
		return resp, nil
	}
	hits := sr.Hits.Hits
	for i := range hits {
		item := ListRespItem{}
		err := json.Unmarshal(hits[i].Source, &item)
		if err != nil {
			return nil, err
		}
		resp = append(resp, item)
	}
	return resp, nil
}

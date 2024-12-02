package main

import (
	"bytes"
	"encoding/json"
	"errors"
	"io"
	"net/http"
)

type (
	LoginService interface {
		Login(r *LoginReq) (string, error)
	}

	loginService struct {
		url string
	}

	LoginReqBody struct {
		Username string
		Password string
	}

	LoginReq struct {
		Header http.Header
		Body   LoginReqBody
	}

	LoginRespData struct {
		AccessToken string `json:"access_token"`
	}

	LoginResp struct {
		Code int           `json:"code"`
		Data LoginRespData `json:"data"`
	}
)

func NewLoginService(url string) LoginService {
	return &loginService{url: url}
}

func (l *loginService) Login(r *LoginReq) (string, error) {
	bs, err := json.Marshal(r.Body)
	if err != nil {
		return "", err
	}
	req, err := http.NewRequest(http.MethodPost, l.url, bytes.NewReader(bs))
	if err != nil {
		return "", err
	}
	resp, err := http.DefaultClient.Do(req)
	if err != nil {
		return "", err
	}
	defer resp.Body.Close()
	bs, err = io.ReadAll(resp.Body)
	if err != nil {
		return "", err
	}

	var loginResp LoginResp
	err = json.Unmarshal(bs, &loginResp)
	if err != nil {
		return "", err
	}

	if loginResp.Code == 0 {
		return loginResp.Data.AccessToken, nil
	}
	return "", errors.New("api call failed, code non-zero (unsuccessful code)")
}

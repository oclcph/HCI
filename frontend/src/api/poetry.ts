import { axios } from "../router/request";
import { POETRY_MODULE } from "./api";

export const getDifferentPoetry = (level: string, type: string, size: number) => {
    return axios.get(`${POETRY_MODULE}/level/type`, {params: {level: level, type: type, size: size}});
}

export const getSentence = (id: number) => {
    return axios.get(`${POETRY_MODULE}/${id}/sentence`);
}

export const getAllPoetry = () => {
    return axios.get(`${POETRY_MODULE}/getAll`);
}

export const addToFavorite = (id: number) => {
    return axios.post(`${POETRY_MODULE}/${id}/fav`);
}

export const getFavorite = () => {
    return axios.get(`${POETRY_MODULE}/fav/get`);
}

export const deleteFavorite = (id: number) => {
    return axios.post(`${POETRY_MODULE}/${id}/unfav`);
}
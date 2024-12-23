import { axios } from "../router/request";
import { POETRY_MODULE } from "./api";

export const getPoetry = (level: string, size: number) => {
    return axios.get(`${POETRY_MODULE}/level`, {params: {level: level, size: size}});
}

export const getDifferentPoetry = (level: string, type: string, size: number) => {
    return axios.get(`${POETRY_MODULE}/level/type`, {params: {level: level, type: type, size: size}});
}

export const getSentence = (id: number) => {
    return axios.get(`${POETRY_MODULE}/${id}/sentence`);
}
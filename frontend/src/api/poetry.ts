import axios from "axios";
import { POETRY_MODULE } from "./api";

export const getPoetry = (level: string, size: number) => {
    return axios.get(`${POETRY_MODULE}/level`, {params: {level: level, size: size}});
}
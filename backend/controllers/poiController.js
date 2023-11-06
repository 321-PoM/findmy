import * as poiService from '../services/poiService.js';
import { controllerErrorHandler } from './controllerErrorHandler.js';

export const getPoi = async (req, res) => {
    try {
        const poi = await poiService.getPoi(req.params.id);
        res.status(200).json(poi);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const createPoi = async (req, res) => {
    try {
        const poi = await poiService.createPoi(req.body);
        res.status(200).json(poi);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const updatePoi = async (req, res) => {
    try {
        const poi = await poiService.updatePoi(req.params.id, req.body);
        res.status(200).json(poi);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const reportPoi = async (req, res) => {
    try{
        const reportReviewRatio = await poiService.reportPoi(req.params.id);
        if(reportReviewRatio >= 1/2){
            const del = await poiService.deletePoi(req.params.id);
        }
        res.status(200).json(reportReviewRatio)
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
}

export const buyPoi = async (req, res) => {
    try{
        const success = await poiService.buyPoi(req.params.id, req.params.buyerId);
        res.status(200).json(success);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
}

export const deletePoi = async (req, res) => {
    try {
        await poiService.deletePoi(req.params.id);
        res.status(204).send();
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const listPois = async (req, res) => {
    try {
        const pois = await poiService.listPois();
        res.status(200).json(pois);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const listFilteredPois = async (req, res) => {
    try {
        const pois = await poiService.listFilteredPois(req.params.longitude, req.params.latitude, req.params.poiType, req.params.distance);
        res.status(200).json(pois);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

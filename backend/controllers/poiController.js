import * as poiService from '../services/poiService.js';
import { controllerErrorHandler } from './controllerErrorHandler.js';
import { BlobServiceClient } from '@azure/storage-blob';

const AZURE_STORAGE_CONNECTION_STRING = process.env.AZURE_STORAGE_CONNECTION_STRING;
const containerName = process.env.AZURE_STORAGE_CONTAINER_NAME;

export const uploadImageToAzure = async (imageBuffer, imageName) => {
    const blobServiceClient = BlobServiceClient.fromConnectionString(AZURE_STORAGE_CONNECTION_STRING);
    const containerClient = blobServiceClient.getContainerClient(containerName);

    const blockBlobClient = containerClient.getBlockBlobClient(imageName);
    await blockBlobClient.upload(imageBuffer, imageBuffer.length);

    const imageUrl = `https://${process.env.AZURE_STORAGE_ACCOUNT_NAME}.blob.core.windows.net/${containerName}/${imageName}`;

    return imageUrl;
};

export const getPoi = async (req, res) => {
    console.log("");
    try {
        const poi = await poiService.getPoi(req.params.id, req.params.userId);
        res.status(200).json(poi);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const createPoi = async (req, res) => {
    try {
        let imageUrl = null;
        if (req.file) {
            const imageFile = req.file;
            const imageOriginalName = imageFile.originalname;
            const imageExtension = imageOriginalName.split('.').pop();
            const imageName = `${Date.now()}.${imageExtension}`;
            imageUrl = await uploadImageToAzure(imageFile.buffer, imageName);
        }

        const poiData = {
            ...req.body,
            latitude: parseFloat(req.body.latitude),
            longitude: parseFloat(req.body.longitude),
            rating: parseInt(req.body.rating),
            reports: parseInt(req.body.reports),
            ownerId: parseInt(req.body.ownerId),
            isDeleted: parseBool(req.body.isDeleted),
            imageUrl: imageUrl
        }

        const poi = await poiService.createPoi(poiData);
        res.status(200).json(poi);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const updatePoi = async (req, res) => {
    console.log("");
    try {
        const poi = await poiService.updatePoi(req.params.id, req.body);
        res.status(200).json(poi);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const reportPoi = async (req, res) => {
    console.log("");
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
    console.log("");
    try{
        const success = await poiService.buyPoi(req.params.id, req.params.buyerId);
        res.status(200).json(success);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
}

export const deletePoi = async (req, res) => {
    console.log("");
    try {
        await poiService.deletePoi(req.params.id);
        res.status(204).send();
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const listPois = async (req, res) => {
    console.log("");
    try {
        const pois = await poiService.listPois(req.params.userId);
        res.status(200).json(pois);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

export const listFilteredPois = async (req, res) => {
    console.log("");
    try {
        const pois = await poiService.listFilteredPois(req.params.longitude, req.params.latitude, req.params.poiType, req.params.distance, req.params.userId);
        res.status(200).json(pois);
    } catch (error) {
        controllerErrorHandler(error, req, res);
    }
};

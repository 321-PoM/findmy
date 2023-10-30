import * as reviewService from '../services/reviewService.js';
import { updatePoi } from '../services/poiService.js';
import { calcPoiRating } from '../services/poiService.js';

export const listReviews = async (req, res) => {
    try{
        const list = await reviewService.listReviews(req.params.searchBy, req.params.id);
        res.status(200).json(list);
    } catch (error) {
        res.status(500).json({ message: "Internal Server Error"});
    }
};

export const getReview = async (req, res) => {
    try{
        const review = await reviewService.getReview(req.params.id);
        res.status(200).json(review);
    } catch (error) {
        res.status(500).json({ message: "Internal Server Error"});
    }
};

export const createReview = async (req, res) => {
    try {
        const poiId = req.body.data.poiId;
        const review = await reviewService.createReview(req.body.data);
        const newRating = await calcPoiRating(poiId);
        const updated = await updatePoi(poiId, {rating: newRating});
        res.status(200).json(review);
    } catch (error) {
        res.status(500).json({ message: "Internal Server Error"});
    }
};

export const updateReview = async (req, res) => {
    try{
        const review = await reviewService.updateReview(req.params.id, req.body.data);
        const newRating = await calcPoiRating(req.body.poiId);
        const update = await updatePoi(req.params.poiId, {rating: newRating});
        res.status(200).json(review);
    } catch (error) {
        res.status(500).json({ message: "Internal Server Error"});
    }
}
  
export const deleteReview = async (req, res) => {
    try{
        const del = await reviewService.deleteReview(req.params.id);
        const newRating = await calcPoiRating(del.poiId);
        const update = await updatePoi(del.poiId, {rating: newRating});
        res.status(200).json(del);
    } catch (error) {
        res.status(500).json({ message: "Internal Server Error"});
    }
}
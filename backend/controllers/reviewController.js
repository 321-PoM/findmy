import * as reviewService from '../services/reviewService.js';
import { updatePoi } from '../services/poiService.js';
import { calcPoiRating } from '../services/poiService.js';

export const listReviews = async (req, res) => {
    try{
        const list = await reviewService.listReviews(req.param.searchBy, req.body.id);
        res.status(200).json(list);
    } catch (error) {
        res.status(500).json({ message: "Internal Server Error"});
    }
};

export const getReview = async (req, res) => {
    try{
        const review = await reviewService.getReview(req.body.poiId, req.body.userId);
        res.status(200).json(review);
    } catch (error) {
        res.status(500).json({ message: "Internal Server Error"});
    }
};

export const createReview = async (req, res) => {
    try {
        const review = await reviewService.createReview(req.body.poiId, req.body.userId, req.body.rating);
        const newRating = await calcPoiRating(req.body.poiId);
        const updated = await updatePoi(req.body.poiId, {rating: newRating});
        res.status(200).json(review);
    } catch (error) {
        res.status(500).json({ message: "Internal Server Error"});
    }
};

export const updateReview = async (req, res) => {
    try{
        const review = await reviewService.updateReview(req.body.poiId, req.body.userId, req.body.data);
        const newRating = await calcPoiRating(req.body.poiId);
        const update = await updatePoi(req.body.poiId, {rating: newRating});
        res.status(200).json(review);
    } catch (error) {
        res.status(500).json({ message: "Internal Server Error"});
    }
}
  
export const deleteReview = async (req, res) => {
    try{
        const del = await reviewService.deleteReview(req.body.poiId, req.body.userId);
        const newRating = await calcPoiRating(req.params.id);
        const update = await updatePoi(req.param.id, {rating: newRating});
        res.status(200).json(del);
    } catch (error) {
        res.status(500).json({ message: "Internal Server Error"});
    }
}
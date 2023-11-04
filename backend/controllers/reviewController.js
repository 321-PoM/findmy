import * as reviewService from '../services/reviewService.js';
import { updatePoi } from '../services/poiService.js';
import { calcPoiRating } from '../services/poiService.js';

export const listReviews = async (req, res) => {
    try{
        const list = await reviewService.listReviews(req.params.searchBy, req.params.id);
        res.status(200).json(list);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

export const getReview = async (req, res) => {
    try{
        const review = await reviewService.getReview(req.params.id);
        res.status(200).json(review);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

export const createReview = async (req, res) => {
    try {
        const review = await reviewService.createReview(req.body.poiId, req.body.userId, req.body.rating, req.body.description);
        const newRating = await calcPoiRating(req.body.poiId);
        const updated = await updatePoi(req.body.poiId, {rating: newRating});
        res.status(200).json(review);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

// Please do not update rating with this method -> use the more specific updateRating
export const updateReview = async (req, res) => {
    try{
        const review = await reviewService.updateReview(req.params.id, req.body.data);
        res.status(200).json(review);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
}
  
export const updateRating = async (req, res) => {
    try{
        const newRating = await reviewService.updateRating(req.params.id, req.body.data);
        const newPoiRating = await calcPoiRating(newRating.poiId);
        const update = await updatePoi(newRating.poiId, {rating: newPoiRating});
        res.status(200).json(newRating);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
}

export const deleteReview = async (req, res) => {
    try{
        const del = await reviewService.deleteReview(req.params.id);
        const newRating = await calcPoiRating(del.poiId);
        const update = await updatePoi(del.poiId, {rating: newRating});
        res.status(200).json(del);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
}

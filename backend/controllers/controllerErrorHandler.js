/* Centralized error handler for controllers. */
export const controllerErrorHandler = (err, req, res) => {
    console.log(err);
    // Errors from contoller are guaranteed to be server side fault.
    res.status(500).json({ message: err.message });
}; 

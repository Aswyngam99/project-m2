const Object = require('../models/objectModel')
const mongoose = require('mongoose')
// GET all objects
const getObjects = async (req, res) => {
    const objects = await Object.find({}).sort({createdAt: -1})
    res.status(200).json(objects)
}
// GET a single object
const getObject = async (req, res) => {
    const { id } = req.params
    if (!mongoose.Types.ObjectId.isValid(id)){
        return res.status(404).json({error: 'No such object'})
    }
    const object = await Object.findById(id)
    if (!object) {
        return res.status(400).json({error: 'No such object'})
    }
    res.status(200).json(object)
}
// CREATE a new object
const createObject = async (req, res) => {
    const {name, uri} = req.body

    // add doc to db 
    try {
        const object = await Object.create({name, uri})
        res.status(200).json(object)
    } catch (error) {
        res.status(400).json({error: error.message})
    }
}
// DELETE an object

const deleteObject = async (req, res) => {
    const { id } = req.params

    if (!mongoose.Types.ObjectId.isValid(id)){
        return res.status(404).json({error: 'No such object'})
    }
    
    const object = await Object.findOneAndDelete({_id: id})
    if (!object) {
        return res.status(400).json({error: 'No such object'})
    }

    res.status(200).json(object)

}

// UPDATE an object
const updateObject = async (req, res) => {
    const { id } = req.params

    if (!mongoose.Types.ObjectId.isValid(id)){
        return res.status(404).json({error: 'No such object'})
    }

    const object = await Object.findByIdAndUpdate({_id: id}, {
        ...req.body
    })

    if (!object) {
        return res.status(400).json({error: 'No such object'})
    }

    res.status(200).json(object)
}

module.exports = {
    getObject,
    getObjects,
    createObject,
    deleteObject,
    updateObject
}
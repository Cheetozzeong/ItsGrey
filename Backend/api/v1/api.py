from fastapi import APIRouter

from api.v1.endpoints import make_Image, test

api_router = APIRouter()

api_router.include_router(test.router, tags=["Test"])
api_router.include_router(make_Image.router, tags=["Its-Grey"])

from fastapi import APIRouter, Response

router = APIRouter()

@router.get("/api/v1/itsGrey/test")
async def test_request():
    return Response("Your Request Has Arrived Successfully!")

from fastapi import FastAPI
# from starlette.middleware.cors import CORSMiddleware

from api.v1.api import api_router

app = FastAPI()
app.include_router(api_router)

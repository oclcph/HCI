# urls.py
from django.urls import path
from . import views

urlpatterns = [
    path('register', views.register_user, name='register'),  # 注册接口
    path('login', views.login_user, name='login'),  # 登录接口
    path('protected/', views.protected_view, name='protected'),  # 受保护的接口
]

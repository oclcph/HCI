from django.contrib.auth import authenticate
from django.contrib.auth.models import User
from rest_framework_simplejwt.tokens import RefreshToken
from rest_framework import status
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response


# 用户注册视图
@api_view(['POST'])
def register_user(request):
    """
    用户注册接口：
    接收用户名、密码和确认密码，创建新用户，返回 JWT 令牌。
    """
    username = request.data.get('phone')
    password = request.data.get('password')
    password2 = request.data.get('confirmPassword')

    # 确认密码是否一致
    if password != password2:
        return Response({
            'error': 'Passwords do not match.'
        }, status=status.HTTP_400_BAD_REQUEST)

    # 检查用户名是否已存在
    if User.objects.filter(username=username).exists():
        return Response({
            'error': 'Username is already taken.'
        }, status=status.HTTP_400_BAD_REQUEST)

    # 创建新用户
    user = User.objects.create_user(username=username, password=password)

    # 生成 JWT
    refresh = RefreshToken.for_user(user)
    access_token = str(refresh.access_token)

    return Response({
        'message': 'User registered successfully.',
        'access_token': access_token,
    }, status=status.HTTP_201_CREATED)


# 用户登录视图
@api_view(['POST'])
def login_user(request):
    """
    用户登录接口：
    接收用户名和密码，验证用户，返回 JWT 令牌。
    """
    username = request.data.get('phone')
    password = request.data.get('password')

    # 验证用户名和密码
    user = authenticate(request, username=username, password=password)

    if user is not None:
        # 生成 JWT
        refresh = RefreshToken.for_user(user)
        access_token = str(refresh.access_token)

        return Response({
            'message': 'Login successful',
            'access_token': access_token,
        }, status=status.HTTP_200_OK)
    else:
        return Response({
            'error': 'Invalid username or password',
        }, status=status.HTTP_400_BAD_REQUEST)


# 受保护的视图：只有登录用户可以访问
@api_view(['GET'])
@permission_classes([IsAuthenticated])  # 需要认证用户访问
def protected_view(request):
    """
    受保护的接口：仅允许认证用户访问
    """
    return Response({
        'message': 'This is a protected view.',
        'user': request.user.username,  # 返回当前用户信息
    })

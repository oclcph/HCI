from django.contrib import admin
from .models import Poetry, Sentence

# 内联 Sentence 模型
class SentenceInline(admin.TabularInline):
    model = Sentence  # 关联的模型
    extra = 1  # 显示 1 个空白行，便于直接添加新的 Sentence

# Poetry 模型的后台配置
@admin.register(Poetry)
class PoetryAdmin(admin.ModelAdmin):
    list_display = ('id', 'title', 'author', 'dynasty', 'poem_type', 'difficulty')  # 添加 id 字段
    search_fields = ('title', 'author', 'dynasty')
    list_filter = ('poem_type', 'difficulty')
    inlines = [SentenceInline]  # 将 Sentence 嵌入到 Poetry 的后台页面

    # 定义字段分组，在详情页面中显示 id 字段
    fieldsets = (
        (None, {
            'fields': ('id', 'title', 'author', 'dynasty', 'poem_type', 'difficulty'),
        }),
    )
    readonly_fields = ('id',)  # 使 id 字段只读，避免误修改

# Sentence 模型的后台配置
@admin.register(Sentence)
class SentenceAdmin(admin.ModelAdmin):
    list_display = ('id', 'first_part', 'second_part', 'poetry')  # 在 Sentence 后台显示关联的 Poetry
    search_fields = ('first_part', 'second_part', 'poetry__title')  # 可搜索
    list_filter = ('poetry',)  # 可按 Poetry 过滤

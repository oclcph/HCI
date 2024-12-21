from django.db import models

# Create your models here.
from django.db import models

# Poetry 模型
class Poetry(models.Model):
    # 枚举类型 1：诗的类型
    class PoemType(models.TextChoices):
        FIVE_WORDS = '五言诗', '五言诗'
        SEVEN_WORDS = '七言诗', '七言诗'
        OTHER_WORDS='其他','其他'

    # 枚举类型 2：诗的难度
    class Difficulty(models.TextChoices):
        LOW = 'low', 'Low'
        MID = 'mid', 'Mid'
        HIGH = 'high', 'High'

    title = models.CharField(max_length=100)  # 诗的标题
    dynasty = models.CharField(max_length=50)  # 作者的朝代
    author = models.CharField(max_length=50)  # 作者姓名
    poem_type = models.CharField(
        max_length=10,
        choices=PoemType.choices,
        default=PoemType.FIVE_WORDS
    )  # 诗的类型（枚举）
    difficulty = models.CharField(
        max_length=10,
        choices=Difficulty.choices,
        default=Difficulty.MID
    )  # 难度（枚举）

    def __str__(self):
        return f"{self.title} - {self.author} ({self.dynasty})"


# Sentence 模型
class Sentence(models.Model):
    first_part = models.CharField(max_length=100)  # 一行诗的上半句
    second_part = models.CharField(max_length=100)  # 一行诗的下半句
    poetry = models.ForeignKey(Poetry, on_delete=models.CASCADE, related_name='sentences')  # 外键关联 Poetry 模型

    def __str__(self):
        return f"{self.first_part} ... {self.second_part}"

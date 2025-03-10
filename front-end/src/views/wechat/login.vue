import { api } from '@/utils/api-instance'
<script setup lang="ts">
import { ref, onMounted } from 'vue';
import {api} from "@/utils/api-instance";

// 在组件挂载时调用 API 获取二维码图片
const imgurl = ref<string>('https://search-operate.cdn.bcebos.com/7e85570b817e17e8f3ae93134cc78451.gif');
onMounted(() => {
  api.loginController.getQrImg().then((res) => {
    imgurl.value = res.data.qrImgBase64; // 更新 imgurl 的值
  }).catch((error) => {
    console.error('Failed to fetch QR image:', error);
  });
});

// 定义按钮点击事件处理函数
const handleCallBackBtnClick = () => {
  console.log('Callback button clicked');
  api.loginController.setCallBackUrl().then((res) => {
    console.log(res);
  })
  // 在这里添加你想要执行的逻辑
};
 // 示例图片 URL
</script>

<template>
  <div class="image-container">
    <img :src="imgurl" alt="Centered Image">
    <el-button @click="handleCallBackBtnClick">Callback Button</el-button>
  </div>
</template>

<style scoped lang="scss">.image-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh; // 使容器占满整个视口高度，可以根据需要调整
}

img {
  max-width: 100%;
  height: auto;
}
</style>
// 模型列表展示
function getModelList (params) {
  return $axios({
    url: '/model/page',
    method: 'get',
    params
  })
}

// 修改---启用禁用接口
function enableOrDisableModel (params) {
  return $axios({
    url: '/model',
    method: 'put',
    data: { ...params }
  })
}

// 新增模型---添加模型
function addModel (params) {
  return $axios({
    url: '/model',
    method: 'post',
    data: { ...params }
  })
}

// 修改模型---添加模型
function editModel (params) {
  return $axios({
    url: '/model',
    method: 'put',
    data: { ...params }
  })
}

// 根据模型Id进行查询
function queryModelById (id) {
  return $axios({
    url: `/model/${id}`,
    method: 'get'
  })
}
# 新增节点支持开发

* 在com.alibaba.compileflow.idea.graph.model包下新增xxxModel
* 在com.alibaba.compileflow.idea.graph.model.NodeCloneFactory类里面新增copy逻辑
* 在lang.xml新增节点多语言名称
* 在com.alibaba.compileflow.idea.graph.palette.NodeTemplateFactory新增一个Template实例
* 在com.alibaba.compileflow.idea.graph.images.palette目录下新增节点图标
* 在com.alibaba.compileflow.idea.graph.styles下的classic.xml和color.xml文件中定制样式
* 在com.alibaba.compileflow.idea.graph.nodeview.dialog新增一个对话框，用来编辑节点属性并在com.alibaba.compileflow.idea.graph.nodeview.NodeEditDialogFactory里面注册
* 模型转换：com.alibaba.compileflow.idea.graph.codec.NodeConvert.toNode
* 模型转换：com.alibaba.compileflow.idea.graph.codec.NodeConvert.toModel
* 渲染定制：com.alibaba.compileflow.idea.graph.codec.impl.tbbpm.Model2GraphConvert.doVertexDraw
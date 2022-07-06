# multi thread

需要获取大量基础信息时，使用线程池，分批调用，再组装
## 代码
```java
        // 返回结果定义
        List<OrderCargoDto> orderCargoDtos = new ArrayList<>();

        // 用工具类分批
        List<List<String>> splitList = SplitUtils.splitList(skuCodes, 100);

        List<Future<List<OrderCargoDto>>> futureList = new ArrayList<>();
        ExecutorService executorService = new ThreadPoolExecutor(4, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());// 线程池
        // 分批调用
        splitList.forEach(childSkuCodes -> {
        Future<List<OrderCargoDto>> future = executorService.submit(() -> {
        return orderCargoFeignClient.getBatchByCodes(branchCode, skuCodes);// 获取sku信息
        });
        futureList.add(future);
        });
        // 调用结果组装
        for (Future<List<OrderCargoDto>> future : futureList) {
        try {
        orderCargoDtos.addAll(future.get());
        } catch (InterruptedException e) {
        log.error(e.toString());
        } catch (ExecutionException e) {
        log.error(e.toString());
        }
        }
        executorService.shutdown();

        return orderCargoDtos;
```

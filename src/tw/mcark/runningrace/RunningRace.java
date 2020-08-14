package tw.mcark.runningrace;



                p.sendMessage(ChatColor.GREEN+"" +"擊退100格");
                p2.sendMessage(ChatColor.RED+"" +"擊退100格");

            }
            p.getInventory().remove(Material.DIAMOND_SWORD);






        }

    }//攻擊事件
    @EventHandler
    public void onEvent(PlayerInteractAtEntityEvent event) {
        Player p= event.getPlayer();
        if (event.getRightClicked().getName().equals("開始")) {
            event.setCancelled(true);
            join++;
            int i = 20;
            if (count + join == 1) {

                try {
                    while (i >= 0) {
                        Bukkit.broadcastMessage("§2倒數" + "   " + i);
                        i--;
                        Thread.sleep(1000);
                        if (i == -1) {
                            Bukkit.broadcastMessage("§2開始");
                            join+=10;
                        }
                    }
                    Block block = Bukkit.getWorld("world").getBlockAt(112, 60, 152);
                    block.setType(Material.AIR);
                    nether_wart();
                }
                catch(Exception e){

                }
            }

        }





    }//實體互動事件
    @EventHandler
    public void onEvent(EntityPickupItemEvent event) {
        Player p = (Player) event.getEntity();

        if (!p.getInventory().contains(Material.NETHER_WART)&& event.getItem().getItemStack().getType() == Material.NETHER_WART) {//已有疙瘩不能撿

            event.setCancelled(false);
        }
        else if(event.getItem().getItemStack().getType() == Material.ROSE_RED){
             if (p.getInventory().getItemInMainHand().getType() == Material.TOTEM_OF_UNDYING){
                p.sendMessage("自我免疫");
                p.getInventory().remove(Material.TOTEM_OF_UNDYING);

            }
            else if (p.getInventory().getItemInMainHand().getType() == Material.DRAGON_EGG){
                p.sendMessage("自我免疫");
            }
            else{
                 event.getItem().remove();
                 p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20*5, 1));




             }
            p.getInventory().remove(Material.ROSE_RED);


        }//假道具
        else{
            event.setCancelled(true);
        }

    }//實體撿取事件
    public  void  end(Player p){
        p.sendTitle("§6勝利", "", 10, 70, 20);
        Location EndLine = new Location(Bukkit.getWorld("world"), 109, 31, 5442);
        p.teleport(EndLine);
        Block block = Bukkit.getWorld("world").getBlockAt(112, 60, 152);
        block.setType(Material.REDSTONE_BLOCK);
        Ranking[IntRanking] = p.getName();
        IntRanking++;


        reciprocal(1);



        new BukkitRunnable() {
            @Override
            public void run() {
                ArkGame.getInstance().sendGameServerDirective(GameServerDirective.SHUTDOWN, getGameType());
            }
        }.runTaskLater(this, 20*6);




    }//贏家
    public void reciprocal(int a){
        if(count + a==1)
        {
            int i = 10;
            try{
                while(i>=0) {

                    Bukkit.broadcastMessage("§c倒數"+"   "+i);
                    i--;
                    Thread.sleep(1000);
                    if (i == 0) {
                        Bukkit.broadcastMessage("§61st" +"   "+ Ranking[0]);
                        Thread.sleep(1000);
                        Bukkit.broadcastMessage("§62th" +"   "+ Ranking[1]);
                        Thread.sleep(1000);
                        Bukkit.broadcastMessage("§63rd" +"   "+ Ranking[2]);
                    }
                }
                back();

            }catch(Exception e){
            }
        }
    }//倒數10秒
    public void nether_wart(){
        int rangeX = 20;
        int rangeZ = 6000;
        int rangeY = 35;
        new BukkitRunnable() {

            Random random = new Random();

            @Override
            public void run() {
                for (int i = 0; i < 500; i++) {
                    int randomX = random.nextInt(rangeX);
                    int randomZ = random.nextInt(rangeZ);
                    int randomY = random.nextInt(rangeY);
                    Location randomLoc = new Location(Bukkit.getWorld("world"), 122-randomX, 70-randomY,129+randomZ);
                    Item item = randomLoc.getWorld().dropItemNaturally(randomLoc, new ItemStack(Material.NETHER_WART));
                }
            }
        }.runTaskTimer(this, 20*60, 20*15);
    }//生成道具
    public void back(){


    }


    @Override
    public GameType getGameType() {
        return GameType.RUNNINGRACE;
    }

    @Override
    public void onPlayerArrival(HashMap<Integer, HashSet<UUID>> players) {
        ArkGame.getInstance().sendGameServerDirective(GameServerDirective.CLOSE, getGameType());

    }

    @Override
    public void register() {
        ArkGame.getInstance().registerGame(this);
    }

}




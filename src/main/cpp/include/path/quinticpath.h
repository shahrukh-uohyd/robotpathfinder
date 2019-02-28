#pragma once

#include "segment/quinticsegment.h"
#include "path.h"
#include "math/vec2d.h"
#include <stdexcept>
#include <cmath>
#include <iostream>

namespace rpf {
    class QuinticPath : public Path {
    public:
        QuinticPath(const std::vector<Waypoint> &waypoints, double alpha) {
            this->waypoints = waypoints;
            this->alpha = alpha;
            type = PathType::CUBIC_HERMITE;

            if(waypoints.size() < 2) {
                throw std::runtime_error("Not enough waypoints");
            }

            for(int i = 0; i < waypoints.size() - 1; i ++) {
                std::cout << waypoints[i].heading << std::endl;
                std::cout << std::cos(waypoints[i].heading) * alpha << std::endl;
                std::cout << std::sin(waypoints[i].heading) * alpha << std::endl;
                segments.push_back(std::make_unique<QuinticSegment>(
                    static_cast<Vec2D>(waypoints[i]), static_cast<Vec2D>(waypoints[i + 1]),
                    Vec2D(std::cos(waypoints[i].heading) * alpha, std::sin(waypoints[i].heading) * alpha),
                    Vec2D(std::cos(waypoints[i + 1].heading) * alpha, std::sin(waypoints[i + 1].heading) * alpha),
                    Vec2D(0, 0), Vec2D(0, 0)
                ));
            }
        }
    };
}
